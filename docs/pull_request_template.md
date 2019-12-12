### JIRA Ticket

Fixes [<PROJECT>-XXXX](https://upside-services.atlassian.net/browse/<PROJECT>-XXXX)

### Changes proposed in PR:
- _change list..._

### How was this tested?
- _one or more test steps..._

#### Database Migrations
_If no database changes are present, feel free to remove this section_

- _one or more database change descriptions..._

Tag @upside-services/db-patch-reviewers group for review.

### Reviewer Checklist

_Full code review checklist is [here](https://upside-services.atlassian.net/wiki/spaces/ENG/pages/9896057/Code+review)._
 
**Correctness:**
- [ ] Is it functionally correct? Does it solve the problem it is meant to solve?
 
**Test Coverage:**
- [ ] Are appropriate unit or integration tests part of the commit?
 
**Readability:**
- [ ] Are variable names accurate?
- [ ] Functions decomposed to ~10-20 lines?
- [ ] Documentation sufficient but not overly verbose?
 
 **Security:**
- [ ] Is all external input validated?
- [ ] Encryption (at rest, in transit, etc) is appropriately applied
- [ ] Exceptions don't reveal sensitive information to the end user
- See also: https://upside-services.atlassian.net/wiki/spaces/ENG/pages/94404617/Code+Review+-+Ancillary+Security+Checklist

**Forensics:**
- [ ] Is the code logging at the right level at 'interesting' places?
- [ ] Is the logger not logging any PII?
- [ ] Does it expose useful metrics?

**Database Migrations:**
- [ ] Were there any database migrations? If so, have they been vetted for impact during deployment?
